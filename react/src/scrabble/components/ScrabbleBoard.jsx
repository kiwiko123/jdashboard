import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';
import { onDragStart } from '../../common/js/drag';
import TextCell from './TextCell';
import { PLAYER_IDS } from '../js/constants';

import '../styles/ScrabbleBoard.css';

function onDragTileOutOfBoard(event, tile) {
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
        const isOccupied = Boolean(tile);
        const key = `cell-${rowIndex}${columnIndex}`;
        const className = classnames('border-white', 'parent-center', 'game-cell', {
            clickable: isOccupied,
        });
        const text = get(tile, 'character');
        const submittedTile = {
            ...tile,
            row: rowIndex,
            column: columnIndex,
        };

        return (
            <TextCell
                key={key}
                className={className}
                text={text}
                draggable={isOccupied && tile.playerId === PLAYER_IDS.PLAYER}
                onDragStart={event => onDragTileOutOfBoard(event, submittedTile)}
                onDrop={event => this.props.dropTileHandler(event, rowIndex, columnIndex)}
            />
        );
    }
}