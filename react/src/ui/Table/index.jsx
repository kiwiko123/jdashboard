import React from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import './Table.css';

const Table = ({
    className, headers, rows, style,
}) => {
    const divClassName = classnames('Table', className);

    let headerArea;
    if (headers && headers.length > 0) {
        const headerElements = headers.map(header => (
            <div
                key={header.label}
                className={classnames('column', style)}
            >
                {header.label}
            </div>
        ));
        headerArea = (
            <div className={classnames('header', 'row', style)}>
                {headerElements}
            </div>
        );
    }

    const rowElements = rows.map((row) => {
        const cells = row.columns.map(column => {
            const content = typeof column.content === 'function'
                ? column.content()
                : column.content;

            return (
                <div
                    key={column.name}
                    className={classnames('column', style)}
                >
                    {content}
                </div>
            );
        });

        return (
            <div
                key={row.name}
                className={classnames('row', style)}
            >
                {cells}
            </div>
        );
    });

    return (
        <div className={divClassName}>
            {headerArea}
            {rowElements}
        </div>
    );
};

Table.propTypes = {
    className: PropTypes.string,
    headers: PropTypes.arrayOf(PropTypes.shape({
        label: PropTypes.string.isRequired,
    })),
    rows: PropTypes.arrayOf(PropTypes.shape({
        columns: PropTypes.arrayOf(PropTypes.shape({
            content: PropTypes.oneOfType([PropTypes.node, PropTypes.func]).isRequired,
            name: PropTypes.string.isRequired,
        })),
        name: PropTypes.string.isRequired,
    })),
    style: PropTypes.oneOf(['grid', 'lines']),
};

Table.defaultProps = {
    className: null,
    headers: [],
    rows: [],
    style: 'grid',
}

export default Table;