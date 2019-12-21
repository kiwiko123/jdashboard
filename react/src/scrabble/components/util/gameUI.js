import React from 'react';
import classnames from 'classnames';
import TextCell from '../TextCell';


export function renderPlayerTiles(tiles) {
    const elements = tiles.map(_renderTile);
    return (
        <div className="row">
            {elements}
        </div>
    )
}

export function onDragStart(event, text, dropEffect = 'move') {
    event.dataTransfer.setData('text/plain', text);
    event.dataTransfer.dropEffect = dropEffect;
}

function _renderTile(tile, index) {
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
