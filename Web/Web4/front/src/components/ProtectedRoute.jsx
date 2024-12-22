import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

import {Navigate} from 'react-router-dom';

// -------------------------------------------------------------------------------------------------------- //

export function ProtectedRoute({allow, redirect, children}) {
    return (
        allow ? children : <Navigate to={redirect} replace/>
    );
}

// -------------------------------------------------------------------------------------------------------- //

ProtectedRoute.propTypes = {
    allow: PropTypes.bool.isRequired,
    redirect: PropTypes.string.isRequired,
    children: PropTypes.node,
}
