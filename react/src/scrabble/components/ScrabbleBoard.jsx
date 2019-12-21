import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import { onDragStart } from './util/gameUI';
import TextCell from './TextCell';

import '../styles/ScrabbleBoard.css';

function onDragTileOutOfBoard(event, tile) {
    event.preventDefault();
    const payload = JSON.stringify(tile);
    onDragStart(event, payload);
}

export default class ScrabbleBoard extends Component {
    static propTypes = {
        board: PropTypes.arrayOf(
            PropTypes.arrayOf(
                PropTypes.shape({
                    character: PropTypes.string.isRequired,
                    playerId: PropTypes.string.isRequired,
                }),
            ),
        ).isRequired,
        submittedTiles: PropTypes.arrayOf(PropTypes.shape({
            character: PropTypes.string.isRequired,
            row: PropTypes.number.isRequired,
            column: PropTypes.number.isRequired,
        })),
        dropTileHandler: PropTypes.func.isRequired,
        clickTileHandler: PropTypes.func,
    };

    static defaultProps = {
        board: [],
        submittedTiles: [],
        clickTileHandler: null,
    };

    render() {
        const board = this.props.board.map((row, index) => this._getRow({
            row,
            rowIndex: index,
        }));

        return (
            <div className="ScrabbleBoard">
                {board}
            </div>
        );
    }

    _getRow({ row, rowIndex }) {
        const key = `row-${rowIndex}`;
        const cells = row.map((tile, columnIndex) => this._getCell({
            tile,
            rowIndex,
            columnIndex,
        }));

        return (
            <div
                key={key}
                className="row"
            >
                {cells}
            </div>
        );
    }

    _getCell({ tile, rowIndex, columnIndex }) {
        const key = `cell-${rowIndex}${columnIndex}`;
        const className = classnames('border-white', 'parent-center', 'game-cell');
        const text = get(tile, 'character');

        return (
            <TextCell
                key={key}
                className={className}
                text={text}
                draggable={!tile}
                onDragStart={event => onDragTileOutOfBoard(event, tile)}
                onDrop={event => this.props.dropTileHandler(event, rowIndex, columnIndex)}
            />
        );
    }
}