export default function bindFunction(fn, callback) {
    return (args) => {
        fn(args);
        callback(args);
    };
}