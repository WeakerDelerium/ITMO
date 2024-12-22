import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useNavigate} from "react-router-dom";

// -------------------------------------------------------------------------------------------------------- //

import {IconButton} from "./IconButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

export function LinkIconButton({path, to, handler, className, style, ...rest}) {

    const setPage = useNavigate();

    function handleClick() {
        if (handler) handler();
        setPage(to);
    }

    return (
        <IconButton path={path}
                    className={className}
                    style={style}
                    onClick={handleClick}
                    {...rest}/>
    );

}

// -------------------------------------------------------------------------------------------------------- //

LinkIconButton.propTypes = {
    path: PropTypes.string.isRequired,
    to: PropTypes.string.isRequired,
    handler: PropTypes.func,
    className: PropTypes.string,
    style: PropTypes.object,
}