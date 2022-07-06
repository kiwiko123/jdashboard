import StateManager from 'state/StateManager';
import { find, get, identity, set, sumBy } from 'lodash';
import Request from 'common/js/Request';
import logger from 'common/js/logging';
import { getUrlParameters, updateQueryParameters } from 'common/js/urltools';

const PLAYER_ID = 'player';
const OPPONENT_ID = 'opponent';

export default class PazaakGameStateTransmitter extends StateManager {
    constructor() {
        super();
        this.initialize();
    }

    initialize() {
        this.addState({
            actions: {
                endTurn: this.endTurn.bind(this),
                selectHandCard: this.selectHandCard.bind(this),
            },
        });

        const { gameId } = getUrlParameters();
        if (gameId) {
            this.loadGame(gameId);
        } else {
            this.createNewGame();
        }
    }

    createNewGame() {
        const payload = {
            playerId: PLAYER_ID,
            opponentId: OPPONENT_ID,
        };

        Request.to('/pazaak/api')
            .withAuthentication()
            .withResponseExtractor(e => e)
            .withBody(payload)
            .post()
            .then((game) => this.updateGameState(game));
    }

    loadGame(gameId) {
        Request.to(`/pazaak/api/games/${gameId}`)
            .withAuthentication()
            .withResponseExtractor(identity)
            .get()
            .then((game) => {
                if (game) {
                    this.updateGameState(game);
                } else {
                    this.createNewGame();
                }
            });
    }

    updateGameState(game) {
        updateQueryParameters({ gameId: game.gameId });

        const playerCards = get(game, 'player.placedCards', []);
        const opponentCards = get(game, 'opponent.placedCards', []);

        const playerScore = sumBy(playerCards, card => card.modifier);
        const opponentScore = sumBy(opponentCards, card => card.modifier);

        set(game, 'player.score', playerScore);
        set(game, 'opponent.score', opponentScore);

        this.setState({
            player: game.player,
            opponent: game.opponent,
            gameId: game.gameId,
            currentPlayerId: game.currentPlayerId,
            winningPlayerId: game.winningPlayerId,
        });
    }

    endTurn(playerId) {
        const player = playerId === this.state.player.id ? this.state.player : this.state.opponent;
        const payload = {
            playerId,
            playedCard: player.selectedHandCard,
        };

        Request.to(`/pazaak/api/games/${this.state.gameId}/end-turn`)
            .withAuthentication()
            .withBody(payload)
            .withResponseExtractor(identity)
            .post()
            .then((response) => {
                this.handleEndTurnResponse(response);
            });
    }

    handleEndTurnResponse(response) {
        if (response.errorMessage) {
            // handle errors
            logger.info(`Pazaak error message: ${response.errorMessage}`);
            this.addState({ errorMessage: response.errorMessage });
            this.render();
            return;
        }

        const { game } = response;
        this.updateGameState(game);

        if (game.winningPlayerId) {
            return;
        }

        if (game.currentPlayerId === game.opponent.id) {
            setTimeout(() => {
                this.endTurn(game.opponent.id);
            }, 1000);
        }
    }

    selectHandCard(cardId) {
        if (this.state.player.selectedHandCard) {
            return;
        }

        const selectedHandCard = find(this.state.player.handCards, card => card.id === cardId);
        const payload = {
            playerId: this.state.player.id,
            selectedHandCard,
        };

        Request.to(`/pazaak/api/games/${this.state.gameId}/select-hand-card`)
            .withAuthentication()
            .withBody(payload)
            .withResponseExtractor(identity)
            .post()
            .then((game) => {
                this.updateGameState(game);
                const { player } = this.state;
                player.selectedHandCard = selectedHandCard;
                this.setState({ player });
            });
    }
}