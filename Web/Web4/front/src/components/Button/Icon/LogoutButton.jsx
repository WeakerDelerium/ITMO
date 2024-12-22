import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {useDispatch} from "react-redux";
import {logout} from "../../../redux/actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

import {LinkIconButton} from "./LinkIconButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {axiosLogout} from "../../../utils/axios.js";

// -------------------------------------------------------------------------------------------------------- //

export function LogoutButton({path, to, className, style, ...rest}) {

    const dispatch = useDispatch();
    const handleLogout = () => dispatch(logout());

    function handleClick() {
        handleLogout();
        axiosLogout();
    }

    return (
        <LinkIconButton path={path}
                        to={to}
                        handler={handleClick}
                        className={className}
                        style={style}
                        {...rest}/>
    );

}

// -------------------------------------------------------------------------------------------------------- //

LogoutButton.propTypes = {
    path: PropTypes.string.isRequired,
    to: PropTypes.string.isRequired,
    className: PropTypes.string,
    style: PropTypes.object,
}
