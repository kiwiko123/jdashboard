import React, { createContext, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

const METADATA_TEMPLATE = {
  __renderCount: 0,
};

export const ControllerContext = createContext({
  ...METADATA_TEMPLATE,
  render: (component) => null,
});

const SinglePageApp = ({
  children, className, maxHistoryLimit,
}) => {
  const metadataRef = useRef({ ...METADATA_TEMPLATE });
  const [renderedChild, setRenderedChild] = useState(children);
  const renderedViewsRef = useRef({
        position: 0,
        views: [renderedChild],
      });

  const renderComponent = (component) => {
    setRenderedChild(component);

    if (renderedViewsRef.current.position >= maxHistoryLimit) {
      renderedViewsRef.current.views.shift();
      --renderedViewsRef.current.position;
    }
    renderedViewsRef.current.views.push(component);
    ++renderedViewsRef.current.position;

    ++metadataRef.current.__renderCount;
  };

  const renderPrevious = () => {
    const { position, views } = renderedViewsRef.current;
    if (position <= 0) {
      // Cannot go back
      return;
    }
    setRenderedChild(renderedViewsRef.current.views[--renderedViewsRef.current.position])
  };

  const controller = {
    ...metadataRef.current,
    render: renderComponent,
    renderPrevious,
  };

  return (
    <div className={classnames('SinglePageApp', className)}>
      <ControllerContext.Provider value={controller}>
        <div className="__delegate-child">
          {renderedChild}
        </div>
      </ControllerContext.Provider>
    </div>
  );
};

SinglePageApp.propTypes = {
  children: PropTypes.node,
  className: PropTypes.string,
  maxHistoryLimit: PropTypes.number,
};

SinglePageApp.defaultProps = {
  children: null,
  className: null,
  maxHistoryLimit: 10,
};

export default SinglePageApp;