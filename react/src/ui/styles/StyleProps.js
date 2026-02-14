import PropTypes from 'prop-types';

const Styles = {
    glass: 'glass',
};

const StyleProps = {
    style: PropTypes.oneOf(['default', Styles.glass]),
};

const StyleDefaultProps = {
    style: null,
};

export {
  Styles,
  StyleProps,
  StyleDefaultProps,
};