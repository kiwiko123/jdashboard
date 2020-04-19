import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import { get } from 'lodash';

import IconButton from '../../common/components/IconButton';
import ScrabbleBoard from './ScrabbleBoard';
import ClickTarget from '../../common/components/ClickTarget';
import TextCell from './TextCell';

import { onDragStart } from '../../common/js/drag';

import playerPropType from '../propTypes/playerPropType';
import tilePropType from '../propTypes/tilePropType';

import '../styles/ScrabbleGame.css';

function renderPlayerTile(tile, index) {
    const key = `tiles-player-available-${index}`;
    const classNames = classnames('border-white', 'parent-center', 'game-cell', 'clickable');
    const payload = JSON.stringify(tile);

    return (
        <TextCell
            key={key}
            className={classNames}
            text={tile.character}
            draggable={true}
            onDragStart={event => onDragStart(event, payload)}
        />
    );
}

export default class ScrabbleGame extends Component {

    static propTypes = {
        board: PropTypes.arrayOf(
           PropTypes.arrayOf(tilePropType),
       ).isRequired,
        player: playerPropType.isRequired,
        opponent: playerPropType.isRequired,
        actions: PropTypes.shape({
            newGame: PropTypes.func.isRequired,
            dropTileOnBoard: PropTypes.func.isRequired,
            onPlayerDropTileOutOfBoard: PropTypes.func.isRequired,
            recallTiles: PropTypes.func.isRequired,
            submitTiles: PropTypes.func.isRequired,
        }).isRequired,
        canSubmit: PropTypes.bool,
        invalidSubmittedTiles: PropTypes.arrayOf(tilePropType),
    };

    static defaultProps = {
        canSubmit: false,
        invalidSubmittedTiles: [],
    };

    render() {
        const playerTiles = get(this.props.player, 'availableTiles', [])
            .map(renderPlayerTile);

        return (
            <ClickTarget
                className="ScrabbleGame"
                onDrop={this.props.actions.onPlayerDropTileOutOfBoard}
            >
                <div className="board parent-center">
                    <ScrabbleBoard
                        board={this.props.board.board}
                        dropTileHandler={this.props.actions.dropTileOnBoard}
                    />
                </div>
                <hr />
                <div className="player-tiles parent-center">
                    <div className="row">
                        {playerTiles}
                    </div>
                </div>
                <div className="buttons parent-center">
                    <IconButton
                        variant='success'
                        fontAwesomeClassName="fas fa-paper-plane"
                        onClick={this.props.actions.submitTiles}
                        disabled={!this.props.canSubmit}
                    >
                        Submit
                    </IconButton>
                    <IconButton
                        variant="secondary"
                        fontAwesomeClassName="fas fa-arrow-down"
                        onClick={this.props.actions.recallTiles}
                        disabled={this.props.player.submittedTiles.length === 0}
                    >
                        Recall
                    </IconButton>
                    <IconButton
                        variant="light"
                        fontAwesomeClassName="fas fa-slash"
                        onClick={() => {}}
                    >
                        Pass
                    </IconButton>
                    <IconButton
                        variant="danger"
                        fontAwesomeClassName="fas fa-redo"
                        onClick={this.props.actions.newGame}
                    >
                        Start Over
                    </IconButton>
                </div>
            </ClickTarget>
        );
    }
}