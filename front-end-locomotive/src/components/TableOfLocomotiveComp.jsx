import React from 'react';
import { Table, TableHead, TableRow, TableCell, TableBody, Chip } from '@mui/material';

function LocomotiveTable({ summaryData }) {
    const formatDate = (updatedAt) => {
        console.log('date', updatedAt)
        const parts = updatedAt.split(',');
        const year = parts[0];
        const month = parts[1];
        const day = parts[2];
        const hour = parts[3];
        const minute = parts[4];
        const second = parts[5];

        return `${month}/${day}/${year} ${hour}:${minute}:${second}`;
    };

    const tableHeaders = [
        { id: 'locoId', label: 'Code' },
        { id: 'locoName', label: 'Name' },
        { id: 'active', label: 'Active' },
        { id: 'notActive', label: 'Not Active' },
        { id: 'maintenance', label: 'Maintenance' },
        { id: 'totalLocomotive', label: 'Total' },
        { id: 'updatedAt', label: 'Updated At' },
    ];

    return (
        <Table sx={{ borderRadius: '12px', overflow: 'hidden' }}>
            <TableHead>
                <TableRow>
                    {tableHeaders.map((header) => (
                        <TableCell key={header.id} align="center">
                            {header.label}
                        </TableCell>
                    ))}
                </TableRow>
            </TableHead>
            <TableBody>
                {summaryData.map((row) => (
                    <TableRow key={row.locoId}>
                        {tableHeaders.map((header) => (
                            <TableCell
                                key={header.id}
                                align="center"
                                sx={{
                                    borderBottom: '1px solid rgba(224, 224, 224, 1)',
                                    borderRadius: '0',
                                    paddingY: '15px',
                                }}
                            >
                                {header.id === 'active' || header.id === 'notActive' || header.id === 'maintenance' || header.id === 'totalLocomotive' ? 
                                (
                                    <Chip
                                        label={row[header.id]}
                                        color="primary"
                                        sx={{
                                            padding: '10px 8px',
                                            height: 0,
                                            backgroundColor:
                                                header.id === 'active'
                                                    ? '#36AE7C'
                                                    : header.id === 'notActive'
                                                        ? '#EB5353'
                                                        : header.id === 'maintenance'
                                                            ? '#F9D923'
                                                            : 'default',
                                            color: '#FFFFFF',
                                        }}
                                    />
                                ) : header.id === 'updatedAt' ? (
                                    <span>
                                        {row[header.id] ? 
                                            formatDate(row[header.id].toString()) : null
                                        }
                                    </span>
                                ) : (
                                    row[header.id]
                                )}
                            </TableCell>
                        ))}
                    </TableRow>
                ))}
            </TableBody>
        </Table>
    );
}

export default LocomotiveTable;