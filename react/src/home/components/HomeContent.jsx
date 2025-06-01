import React, { useContext } from 'react';
import IconButton from '../../common/components/IconButton';
import { ControllerContext } from 'ui/SinglePageApp';

import './HomeContent.css';

const HomeContent = ({ push }) => {
    const controller = useContext(ControllerContext);
    const nextClick = () => {
        controller.render(() => (
            <div className="HomeTest">
                <span>
                    You have gone to the next page!
                </span>
                <IconButton onClick={controller.renderPrevious}>
                    Go back!
                </IconButton>
            </div>
        ));
    };

    return (
        <div className="HomeContent">
            <span>
                Hi! Welcome to Jdashboard.
            </span>
            <IconButton
                onClick={() => push({ recipientUserId: 1, message: 'Hello!' })}
            >
                Press me!
            </IconButton>
            <IconButton
                onClick={nextClick}
            >
                Press me to re-render!
            </IconButton>
        </div>
    );
};

// const HomeContent = ({ push }) => {
//     return (
//         <div className="HomeContent">
//             <span>
//                 Hi! Welcome to Jdashboard.
//             </span>
//             <IconButton
//                 onClick={() => push({ recipientUserId: 1, message: 'Hello!' })}
//             >
//                 Press me!
//             </IconButton>
//         </div>
//     );
// };

export default HomeContent;