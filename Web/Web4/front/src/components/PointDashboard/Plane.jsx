import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useRef, useState, useEffect, Fragment} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {useSelector} from "react-redux";

// -------------------------------------------------------------------------------------------------------- //

import {getPointFormData, status} from "../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./PointDashboard.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Plane({length, handler, ui = uiDefault, ...rest}) {

    const svg = useRef();

    function handleRChange() {
        setRSerif(() => R ? R : "R");
        setRDiv2Serif(() => R ? R / 2 : "R/2");
    }

    const R = useSelector(st => st.R);
    useEffect(handleRChange, [R]);

    const [RSerif, setRSerif] = useState("R");
    const [RDiv2Serif, setRDiv2Serif] = useState("R/2");

    function buildFormData(event) {
        const plane = svg.current;

        let point = plane.createSVGPoint();

        point.x = event.clientX;
        point.y = event.clientY;

        point = point.matrixTransform(plane.getScreenCTM().inverse());

        const scale = R / (2 * seg);

        point.x *= scale;
        point.y *= -scale;

        return getPointFormData(point.x, point.y, R);
    }

    const handleClick = event => R ? handler(status.OK, buildFormData(event)) : handler(status.EMPTY_R);

    const half = length / 2;
    const seg = half / 3;
    const mcr = seg / 10;
    const tick = mcr / 2;

    const axisData = [{x1: -half, y1: 0, x2: half, y2: 0}, {x1: 0, y1: half, x2: 0, y2: -half}];
    const arrowData = [{x: half, y: 0, dx: -2 * mcr, dy: mcr, c: -1}, {x: 0, y: -half, dx: mcr, dy: 2 * mcr, c: 1}];
    const serifData = [-2 * seg, -seg, seg, 2 * seg];
    const labelData = [{c: 'X', dx: 0, dy: 2.5 * mcr, cx: 1, cy: 0}, {c: 'Y', dx: mcr, dy: mcr, cx: 0, cy: 1}];
    const labelValues = ['-' + RSerif, '-' + RDiv2Serif, RDiv2Serif, RSerif];

    return (
        <svg ref={svg}
             width={length}
             height={length}
             viewBox={`${-half} ${-half} ${length} ${length}`}
             xmlns="http://www.w3.org/2000/svg"
             className={classNames(styles.plane, ui.class.plane)}
             style={ui.style.plane}
             onClick={handleClick}
             {...rest}>

            {axisData.map(({x1, y1, x2, y2}, i) => (

                <line key={`axis-${i}`}
                      x1={x1} y1={y1}
                      x2={x2} y2={y2}
                      className={classNames(styles.axis, ui.class.axis)}
                      style={ui.style.axis}/>

            ))}

            {arrowData.map(({x, y, dx, dy, c}, i) => (

                <path key={`arrow-${i}`}
                      d={`M ${x + dx} ${y + dy} L ${x} ${y} L ${x - c * dx} ${y + c * dy}`}
                      className={classNames(styles.arrow, ui.class.arrow)}
                      style={ui.style.arrow}/>

            ))}

            {serifData.map((c, i) =>

                [[c, tick, 1], [tick, c, -1]].map(([x, y, cnt], j) => (

                    <line key={`serif-${i}-${j}`}
                          x1={x} y1={y}
                          x2={cnt * x} y2={-cnt * y}
                          className={classNames(styles.serif, ui.class.serif)}
                          style={ui.style.serif}/>

                ))

            )}

            {labelData.map(({c, dx, dy, cx, cy}, i) => (

                <Fragment key={`label-container-${i}`}>

                    <text key={`label-${i}`}
                          x={cx * half - 2 * dx}
                          y={dy - cy * (half - dy)}
                          textAnchor="end"
                          className={classNames(styles.label, ui.class.label)}
                          style={ui.style.label}>{c}</text>

                    {labelValues.map((val, j) => (

                        <text key={`label-${i}-${j}`}
                              x={cx * serifData[j] - dx}
                              y={dy - cy * serifData[j]}
                              textAnchor={cx ? "middle" : "end"}
                              className={classNames(styles.label, ui.class.label)}
                              style={ui.style.label}>{val}</text>

                    ))}

                </Fragment>

            ))}

            <path d={`M 0 0 0 ${-2 * seg} A ${2 * seg} ${2 * seg} 0 0 1 ${2 * seg} 0 L ${2 * seg} ${2 * seg} 0 ${2 * seg} ${-2 * seg} 0 Z`}
                  className={classNames(styles.figure, ui.class.figure)}
                  style={ui.style.figure}/>

        </svg>
    );

}

// -------------------------------------------------------------------------------------------------------- //

Plane.propTypes = {
    length: PropTypes.number.isRequired,
    handler: PropTypes.func.isRequired,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            plane: PropTypes.string,
            axis: PropTypes.string,
            arrow: PropTypes.string,
            serif: PropTypes.string,
            label: PropTypes.string,
            figure: PropTypes.string,
            point: PropTypes.string
        }),
        style: PropTypes.shape({
            plane: PropTypes.object,
            axis: PropTypes.object,
            arrow: PropTypes.object,
            serif: PropTypes.object,
            label: PropTypes.object,
            figure: PropTypes.object,
            point: PropTypes.object
        }),
    }),
}
