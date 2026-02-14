import PropTypes from 'prop-types';
import { StyleProps, StyleDefaultProps } from 'ui/styles/StyleProps';

const standardButtonProps = {
    ...StyleProps,

    children: PropTypes.string.isRequired,

    // https://react-bootstrap.github.io/components/buttons
    variant: PropTypes.oneOf([
        'primary', 'secondary', 'success', 'warning', 'danger', 'info', 'light', 'dark', 'link',
        'outline-primary', 'outline-secondary', 'outline-success', 'outline-warning', 'outline-danger', 'outline-info',
        'outline-light', 'outline-dark', 'outline-link']),

    // https://fontawesome.com/v5/search?m=free
    fontAwesomeClassName: PropTypes.string,

    disabled: PropTypes.bool,
    block: PropTypes.bool,
    onClick: PropTypes.func,
    className: PropTypes.string,
    size: PropTypes.oneOf(["default", "small", "large"]),
    type: PropTypes.oneOf(['button', 'reset', 'submit']),
};

const standardButtonDefaultProps = {
    ...StyleDefaultProps,
    variant: null,
    fontAwesomeClassName: null,
    disabled: false,
    block: false,
    onClick: null,
    className: null,
    size: "default",
    type: 'button',
};

export {
    standardButtonProps,
    standardButtonDefaultProps,
};