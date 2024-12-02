import PropTypes from "prop-types";

// -------------------------------------------------------------------------------------------------------- //

export function Button({value, className, style, ...rest}) {
    return (
        <button type="button"
                className={className}
                style={style}
                {...rest}>{value}</button>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Button.propTypes = {
    value: PropTypes.string,
    className: PropTypes.string,
    style: PropTypes.object,
}