const express = require('express');
const bodyParser = require("body-parser");
const mongoose = require('mongoose');
const { Kafka, Partitioners } = require('kafkajs');
const schema = require('./models/dataSchemas');
require('./config/database');

//Running port test
const app = express();
const port = 3001;
app.listen(port, () => {
    console.log(`Your Server is running in the port => http://localhost:${port}`);
});

//Kafka
app.use(bodyParser.json());

const kafka = new Kafka({
    clientId: 'my-app',
    brokers: ['localhost:9092']
});

const admin = kafka.admin();

const run = async () => {
    await admin.connect();
    const retentionTime = 2 * 60 * 60 * 60 * 1000;

    try {
        const topic = 'loco';
        const topicExists = await admin.listTopics();

        //Create topic name if it does not exist
        if (!topicExists.includes(topic)) {
            await admin.createTopics({
              topics: [
                {
                  topic,
                  numPartitions: 1,
                  replicationFactor: 1,
                  configEntries: [
                    { name: 'retention.ms', value: `${retentionTime}` }
                  ]
                }
              ]
            });
            console.log(`Topic "${topic}" created successfully.`);
        } else {
            console.log(`Topic "${topic}" already exists.`);
        }

        //Start consumer
        if (topicExists.includes(topic)) {
            const consumer = kafka.consumer({ groupId: 'loco-group' });
            await consumer.connect();
            await consumer.subscribe({ topic: 'loco' });
      
            await consumer.run({
              eachMessage: async ({ message }) => {
                try {
                  const receiveData = JSON.parse(message.value.toString());
                  console.log('/------------------------------------------------------------/\nData received from Kafka:', receiveData);
      
                  const orderMongo = mongoose.model('locomotive_infos', schema);
                  const order = new orderMongo({
                    Code: receiveData.Code,
                    Name: receiveData.Name,
                    Dimension: receiveData.Dimension,
                    Status: receiveData.Status,
                    DateAndTime: receiveData.DateAndTime
                  });
                  
                  const savedOrder = await order.save();
                  console.log('Data saved to MongoDB:', savedOrder);
                  return savedOrder;
                } catch (error) {
                  console.error('Error saving to MongoDB:', error);
                }
              }
            });
        } else {
            console.log('Consumer cannot be started as the topic does not exist.');
        }

    } catch (error) {
        console.error('Error creating or checking topic:', error);
    } finally {
        await admin.disconnect();
    }
};


run().catch(console.error);

app.post("/receive-data", async (req, res) => {
  const producer = kafka.producer({
    createPartitioner: Partitioners.LegacyPartitioner
  });

  await producer.connect();

  const receiveData = req.body;
  console.log("Received data:", receiveData);

  try {
    await producer.send({
      topic: 'loco',
      messages: [{ value: JSON.stringify(receiveData) }]
    });

    console.log('The message send to kafka successfuly:\n/------------------------------------------------------------/', receiveData);
    res.status(200).json({ message: 'The message send to kafka successfuly'});
  } catch (error) {
    console.error('The message send to kafka failed:', error);
    res.status(500).json({ error: 'The message send to kafka failed'});
  } finally {
    await producer.disconnect();
  }
});