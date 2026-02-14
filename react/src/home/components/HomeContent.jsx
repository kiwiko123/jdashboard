import React, { useContext } from 'react';
import StandardButton from 'ui/StandardButton';
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
                <StandardButton onClick={controller.renderPrevious}>
                    Go back!
                </StandardButton>
            </div>
        ));
    };

    return (
        <div className="HomeContent">
            <span>
                Hi! Welcome to Jdashboard.
            </span>
            <StandardButton
//                 onClick={() => push({ recipientUserId: 1, message: 'Hello!' })}
            >
                Press me!
            </StandardButton>
            <StandardButton
                onClick={nextClick}
            >
                Press me to re-render!
            </StandardButton>
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