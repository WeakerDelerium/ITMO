import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useNavigate} from "react-router-dom";

// -------------------------------------------------------------------------------------------------------- //

import {FrameButton} from "./FrameButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

export function LinkButton({url, value, className, style, ...rest}) {

    const setPage = useNavigate();

    const handleClick = () => setPage(url);

    return (
        <FrameButton value={value}
                     className={className}
                     style={style}
                     onClick={handleClick}
                     {...rest}/>
    );

}

// -------------------------------------------------------------------------------------------------------- //

LinkButton.propTypes = {
    url: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
