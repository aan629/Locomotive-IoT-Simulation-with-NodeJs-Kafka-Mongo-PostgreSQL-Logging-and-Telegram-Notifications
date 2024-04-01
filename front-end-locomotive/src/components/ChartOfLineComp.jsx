import {
    Box,
    Text,
    Spinner
} from '@chakra-ui/react'
import ReactApexChart from 'react-apexcharts';
import PropTypes from 'prop-types';

const formatTimestamp = (updatedAt) => {
    const date = new Date(updatedAt[0], updatedAt[1] - 1, updatedAt[2], updatedAt[3], updatedAt[4], updatedAt[5], updatedAt[6] / 1000000);
    const formattedDate = date.toLocaleString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    });
    return formattedDate;
};

ChartOfLineComp.propTypes = {
    data: PropTypes.arrayOf(
        PropTypes.shape({
          locoId: PropTypes.string,
          updatedAt: PropTypes.array,
          totalLocomotive: PropTypes.number,
          active: PropTypes.number,
          notActive: PropTypes.number,
          maintenance: PropTypes.number
        })
    ),
    isFetching: PropTypes.bool,
    isSuccess: PropTypes.bool
};

export default function ChartOfLineComp({ data, isFetching, isSuccess }) {
    const recentData = data ? data.slice(0, 5) : [];

    const timestampsCategories = recentData.map(item => formatTimestamp(item.updatedAt));
    const totalActive = recentData.map(item => item.active);
    const totalNotActive = recentData.map(item => item.notActive);
    const totalMaintenance = recentData.map(item => item.maintenance);

    const options = {
        chart: {
            id: 'spline-area',
            toolbar: {
                show: false,
            },
        },
        xaxis: {
            categories: timestampsCategories,
            axisBorder: {
                show: false, 
            },
            axisTicks: {
                show: false,
            },
            labels: {
                style: {
                    fontWeight: 'bold'
                }
            }
        },
        yaxis: {
            labels: {
                style: {
                    fontWeight: 'bold'
                }
            }
        },
        dataLabels: {
            enabled: false,
        },
        grid: {
            show: false,
        },
        fill: {
            type: 'solid',
            colors: ['transparent'],
        },
        stroke: {
            curve: 'smooth',
            lineCap: 'round', 
        },
        legend: {
            position: 'top',
        }
    };

    const series = [
        {
            name: 'Active',
            data:  totalActive
        },
        {
            name: 'Not Active',
            data: totalNotActive
        },
        {
            name: 'Maintenance',
            data: totalMaintenance
        }
    ];

    return (
        <Box px={18}>
            {isFetching && (
                <Spinner color="blue.500" position="fixed" top={10} right={10} />
            )}
            <Text mt={5} color="#FFFFFF" fontWeight={700}>TOTAL STATUS LOCOMOTIVE</Text>
            {isSuccess && (
                <ReactApexChart options={options} series={series} type="area" height={420} />
            )}
        </Box>
    )
}        