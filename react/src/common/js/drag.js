export function onDragStart(event, text, dropEffect = 'move') {
    event.dataTransfer.setData('text/plain', text);
    event.dataTransfer.dropEffect = dropEffect;
}