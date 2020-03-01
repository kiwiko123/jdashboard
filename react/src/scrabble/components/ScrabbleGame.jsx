import React, { Component } from 'react';
import { get, merge } from 'lodash';
import { RequestService } from '../../common/js/requests';
import {
    NEW_GAME_URL,
    VALIDATE_MOVE_URL,
    PLAY_MOVE_URL,
} from '../js/urls';
import { renderPlayerTiles } from './util/gameUI';

import IconButton from '../../common/components/IconButton';
import ScrabbleBoard from './ScrabbleBoard';
import ClickTarget from '../../common/components/ClickTarget';

import '../styles/ScrabbleGame.css';


const SERVER_URL = 'http://localhost:8080';


function getDefaultState() {
    return {
        board: {
            board: [],
            rowCount: 0,
            columnCount: 0,
            isGameOver: false,
        },
        player: {
            id: null,
            availableTiles: [],
            playedTiles: [],
            submittedTiles: [],
        },
        opponent: {
            id: null,
            availableTiles: [],
            playedTiles: [],
        },
        canSubmit: false,
    };
}


export default class ScrabbleGame extends Component {

    constructor(props) {
        super(props);

        this.state = getDefaultState();

        this._onNewGame = this._onNewGame.bind(this);
        this._updateGameState = this._updateGameState.bind(this);
        this._onDropTileOnBoard = this._onDropTileOnBoard.bind(this);
        this._onPlayerSubmitTiles = this._onPlayerSubmitTiles.bind(this);
        this._onPlayerRecallTiles = this._onPlayerRecallTiles.bind(this);
        this._onPlayerDropTileOutOfBoard = this._onPlayerDropTileOutOfBoard.bind(this);

        this._requestService = new RequestService(SERVER_URL);
        this._onNewGame();
    }

    render() {
        const { availableTiles } = this.state.player;
        const playerTilesElement = renderPlayerTiles(availableTiles);

        return (
            <ClickTarget
                className="ScrabbleGame"
                onDrop={this._onPlayerDropTileOutOfBoard}
            >
                <div className="board parent-center">
                    <ScrabbleBoard
                        board={this.state.board.board}
                        dropTileHandler={this._onDropTileOnBoard}
                    />
                </div>
                <hr />
                <div className="player-tiles parent-center">
                    {playerTilesElement}
                </div>
                <div className="buttons parent-center">
                    <IconButton
                        variant='success'
                        fontAwesomeClassName="fas fa-paper-plane"
                        onClick={this._onPlayerSubmitTiles}
//                         disableOnClick={true}
                        disabled={!this.state.canSubmit}
                    >
                        Submit
                    </IconButton>
                    <IconButton
                        variant="secondary"
                        fontAwesomeClassName="fas fa-arrow-down"
                        onClick={this._onPlayerRecallTiles}
                        disabled={this.state.player.submittedTiles.length === 0}
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
                        onClick={this._onNewGame}
                    >
                        Start Over
                    </IconButton>
                </div>
            </ClickTarget>
        );
    }

    _onNewGame() {
        this._requestService
            .get(NEW_GAME_URL)
            .then(this._updateGameState);

        this.setState({
            canSubmit: false,
        });
    }

    _updateGameState(response) {
        this._requestService.setPersistentPayload({ gameId: response.id });
        this.setState({
            board: get(response, 'board', []),
            player: get(response, 'player', {}),
            opponent: get(response, 'opponent', {}),
        });
    }

    _onDropTileOnBoard(event, rowIndex, columnIndex) {
        event.stopPropagation();
        const payload = event.dataTransfer.getData('text/plain');
        const tile = JSON.parse(payload);
        console.log(JSON.stringify(tile));
        const submittedTile = {
            ...tile,
            row: rowIndex,
            column: columnIndex,
        };

        const { board, player } = this.state;
        const submittedTiles = [...player.submittedTiles, submittedTile];
        const matrix = [...board.board];
        matrix[rowIndex][columnIndex] = tile;

        this.setState({
            board: {
                ...board,
                board: matrix,
            },
            player: {
                ...player,
                // add the newly-dropped tile into the player's submitted tiles
                submittedTiles,
                // remove the newly-dropped tile from the player's available tiles
                availableTiles: player.availableTiles.filter(t => t.id !== tile.id),
            },
            canSubmit: submittedTiles.length > 0,
        });

        this._validatePlayerTiles(submittedTiles);
    }

    _onPlayerSubmitTiles() {
        const payload = {
            player: this.state.player,
        };
        this._requestService.post(PLAY_MOVE_URL, payload);
    }

    _onPlayerRecallTiles() {
        const { player } = this.state;
        this._removeTilesFromBoard(player.submittedTiles);
        this.setState({
            player: {
                ...player,
                availableTiles: [
                    ...player.availableTiles,
                    ...player.submittedTiles,
                ],
                submittedTiles: [],
            },
        });
    }

    _validatePlayerTiles(tiles) {
        const payload = { tiles };
        this._requestService.post(VALIDATE_MOVE_URL, payload);
    }

    _onPlayerDropTileOutOfBoard(event) {
        const payload = event.dataTransfer.getData('text/plain');
        const tile = JSON.parse(payload);
        const player = { ...this.state.player };
        merge(player, {
            availableTiles: [
                ...player.availableTiles,
                tile,
            ],
            submittedTiles: player.submittedTiles.filter(t => t.id !== tile.id),
        });
        this.setState({ player });
        this._removeTilesFromBoard([tile]);
    }

    _removeTilesFromBoard(tiles) {
        const { board, player } = this.state;
        const matrix = [...board.board];
        tiles.forEach((tile) => { matrix[tile.row][tile.column] = null; });

        this.setState({
            board: {
                ...board,
                board: matrix,
            },
        });
    }
}