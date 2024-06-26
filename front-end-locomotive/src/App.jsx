import * as React from 'react'
import {
  Box,
  Container,
  Grid,
  VStack,
  GridItem,
  Stack,
  Text,
  HStack,
} from '@chakra-ui/react'
import ColumnBarComp from './components/ColumnBarComp'
import TotalLocomotiveComp from './components/TotalLocomotiveComp'
import ChartOfLineComp from './components/ChartOfLineComp'
import TableOfLocomotiveComp from './components/TableOfLocomotiveComp'
import { useQuery, QueryClient} from "react-query"
import { dehydrate } from "react-query/hydration"
import { InfoOutlineIcon } from '@chakra-ui/icons'
import { fetchingSummarylatest, fetchingSummaryAll, getStatusSummary } from "./apis/Api"
import { useEffect, useState } from 'react'
import NavBarComp from './components/NavBarComp'

const getSummaryLatest = async () => {
  const response = fetchingSummarylatest()
  return response
}

const getSummaryAll = async (orderBy, page = 0) => {
  const response = fetchingSummaryAll(orderBy, page)
  return response
}

export async function getStaticProps() {
  const queryClient = new QueryClient();

  await Promise.all([
    queryClient.prefetchQuery("latest", () => getSummaryLatest()),
    queryClient.prefetchQuery("allDesc", () => getSummaryAll("Desc")),
    queryClient.prefetchQuery("allAsc", () => getSummaryAll("Asc")),
  ]);

  return {
    props: {
      dehydratedState: dehydrate(queryClient),
    },
  };
}

export default function App() {
  const [page, setPage] = React.useState(0)

  const { data: dataLatest,
    isError: isErrorLatest,
    isLoading: isLoadingLatest,
    isFetching: isFetchingLatest,
    isSuccess: isSuccessLatest
  } = useQuery("latest", () => getSummaryLatest(), {
    refetchInterval: 3600000
  });

  const { data: dataAllAsc, 
    isFetching: isFetchingAllAsc, 
    isSuccess: isSuccessAllAsc 
  } = useQuery("allAsc", () => getSummaryAll("Asc"), {
    refetchInterval: 3600000
  });

  const [summaryLocomotiveData, setSummaryLocomotiveData] = useState([]);
  const fetchLocomotiveData = async () => {
    try {
        const response = await getStatusSummary();
        console.log(response.message);

        if (response.message == "Network Error") {
            throw new Error(response.message);
        } else {
            setSummaryLocomotiveData(response);
        }

    } catch (error) {
        console.error('Error fetching locomotive data:', error);
    }
  };

  useEffect(() => {
    fetchLocomotiveData();

    const locomotiveInterval = setInterval(fetchLocomotiveData, 11001);
    return () => {
        clearInterval(locomotiveInterval);
    };
  }, []);

  const nextPage = () => {
    setPage((old) => old + 1)
  }
  
  const previousPage = () => {
    setPage((old) => old - 1)
  }
  
  return (
    <>
      <NavBarComp/>
      <VStack justifyContent='space-between'>
        <Container maxW="90%">
        <HStack mt={10} mb={5} bgColor={"#6AC5FF"} w="100%" borderRadius={10} justifyContent='center'>
        <InfoOutlineIcon boxSize={20} color='#FFFFFF'/><Text py={3} color='white' fontWeight={500} fontSize={15}> Project Locomotive with NodeJs, Kafka, MongoDB, PostgreeSQL, Logging and Telegram Notification</Text>
        </HStack>
          <Stack gap={10}>
            <VStack>
              <Grid
                h='500px'
                w='100%'
                templateRows='repeat(2, 1fr)'
                templateColumns='repeat(3, 1fr)'
                gap={10}
              >
                <GridItem borderRadius={20} rowSpan={2} colSpan={2} bg='white' border="3px solid #f0f0f0" >
                  <ChartOfLineComp data={dataAllAsc} isFetching={isFetchingAllAsc} isSuccess={isSuccessAllAsc}/>
                </GridItem>
                <GridItem borderRadius={20} rowSpan={1} bg='#F2BE42' >
                  <TotalLocomotiveComp data={dataLatest} isError={isErrorLatest} isLoading={isLoadingLatest} isFetching={isFetchingLatest} isSuccess={isSuccessLatest}/>
                </GridItem>
                <GridItem borderRadius={20} rowSpan={1} bg='#3CBA78' >
                  <ColumnBarComp data={dataLatest} isError={isErrorLatest} isLoading={isLoadingLatest} isFetching={isFetchingLatest} isSuccess={isSuccessLatest}/>
                </GridItem>
              </Grid>
            </VStack>
              <TableOfLocomotiveComp summaryData={summaryLocomotiveData} />
          </Stack>
        </Container>
        <Box bgColor='#20324D' w='100%' textAlign='center' py={3} color='#FFFFFF'>
          <Text fontWeight={700}>Copyright &copy; By Aan Adrian Khothibulumam</Text>
        </Box>
      </VStack>
    </>
  )
}