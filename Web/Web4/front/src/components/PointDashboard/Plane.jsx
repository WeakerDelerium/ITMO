import PropTypes from "prop-types";
import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {useRef, useState, useEffect, useCallback, useMemo, Fragment} from "react";

// -------------------------------------------------------------------------------------------------------- //

import {useSelector} from "react-redux";

// -------------------------------------------------------------------------------------------------------- //

import {getPointFormData, validateX, validateY, status} from "../../utils/form.js";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./PointDashboard.module.scss";
import classNames from "classnames";

// -------------------------------------------------------------------------------------------------------- //

export function Plane({length, XMin, XMax, YMin, YMax, handler, ui = uiDefault, ...rest}) {

    const svg = useRef();

    const R = useSelector(st => st.R);
    const points = useSelector(st => st.points);

    function handleRChange() {
        setRSerif(() => R ? R : "R");
        setRDiv2Serif(() => R ? R / 2 : "R/2");
        handleLCChange();
    }

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

    function handleClick(event) {
        if (!R) return handler(status.EMPTY_R);

        let formData = buildFormData(event);

        !validateX(formData.X) ? handler(status.INVALID_X) : 
        !validateY(formData.Y) ? handler(status.INVALID_Y) :
        handler(status.OK, formData);
    }

    const half = length / 2;
    const seg = half / 3;
    const mcr = seg / 10;
    const tick = mcr / 2;

    const effectData = [
        {prefix: "limit", clazz: classNames(styles.lhatch, ui.class.limitHatch), style: ui.style.limitHatch},
        {prefix: "figure", clazz: classNames(styles.fhatch, ui.class.figureHatch), style: ui.style.figureHatch}
    ];

    const axisData = [{x1: -half, y1: 0, x2: half, y2: 0}, {x1: 0, y1: half, x2: 0, y2: -half}];
    const arrowData = [{x: half, y: 0, dx: -2 * mcr, dy: mcr, c: -1}, {x: 0, y: -half, dx: mcr, dy: 2 * mcr, c: 1}];
    const serifData = [-2 * seg, -seg, seg, 2 * seg];

    const labelData = [{c: 'X', dx: 0, dy: 2.5 * mcr, cx: 1, cy: 0}, {c: 'Y', dx: mcr, dy: mcr, cx: 0, cy: 1}];
    const labelValues = ['-' + RSerif, '-' + RDiv2Serif, RDiv2Serif, RSerif];

    const scaleToSVGCoords = useCallback((val) => R ? (2 * seg * val / R) : -length, [R, length, seg]);
    const validBias = useCallback(val => 0 <= val && val <= half, [half]);
    const nullRect = useMemo(() => ({x: 0, y: 0, w: 0, h: 0}), []);

    const defaultLimitData = useMemo(() => [
        {lim: XMin, f: val => validBias(val) ? {x: -half, y: -half, w: half - val, h: length} : nullRect},
        {lim: XMax, f: val => validBias(val) ? {x: val, y: -half, w: half - val, h: length} : nullRect},
        {lim: YMin, f: val => validBias(val) ? {x: -half, y: val, w: length, h: half - val} : nullRect},
        {lim: YMax, f: val => validBias(val) ? {x: -half, y: -half, w: length, h: half - val} : nullRect},
    ], [XMax, XMin, YMax, YMin, half, length, validBias, nullRect]);

    const limitData = useMemo(() => defaultLimitData.filter(({lim}) => Boolean(lim)), [defaultLimitData]);

    const getLimCoords = useCallback(() => limitData.map(({lim, f}) =>
        f(scaleToSVGCoords(Math.abs(lim)))), [limitData, scaleToSVGCoords]);

    const [limitCoords, setLimitCoords] = useState([]);
    const handleLCChange = useCallback(() => setLimitCoords(getLimCoords), [getLimCoords]);

    useEffect(handleRChange, [R, handleLCChange]);

    return (
        <div>
            <svg ref={svg}
                 width={length}
                 height={length}
                 viewBox={`${-half} ${-half} ${length} ${length}`}
                 xmlns="http://www.w3.org/2000/svg"
                 className={classNames(styles.plane, ui.class.plane)}
                 style={ui.style.plane}
                 onClick={handleClick}
                 {...rest}>

                <defs className="effects">
                    {effectData.map(({prefix, clazz, style}, i) => (
                        <pattern key={`effect-${i}`}
                                 id={`${prefix}-hatch`}
                                 patternUnits="userSpaceOnUse"
                                 patternTransform="rotate(45)"
                                 width={mcr}
                                 height={mcr}
                                 className={clazz}
                                 style={style}>

                            <line x1={0} y1={0}
                                  x2={0} y2={mcr}/>

                        </pattern>
                    ))}
                </defs>

                <g className="axes">
                    {axisData.map(({x1, y1, x2, y2}, i) => (
                        <line key={`axis-${i}`}
                              x1={x1} y1={y1}
                              x2={x2} y2={y2}
                              className={classNames(styles.axis, ui.class.axis)}
                              style={ui.style.axis}/>
                    ))}
                </g>

                <g className="arrows">
                    {arrowData.map(({x, y, dx, dy, c}, i) => (
                        <path key={`arrow-${i}`}
                              d={`M ${x + dx} ${y + dy} L ${x} ${y} L ${x - c * dx} ${y + c * dy}`}
                              className={classNames(styles.arrow, ui.class.arrow)}
                              style={ui.style.arrow}/>
                    ))}
                </g>

                <g className="serifs">
                    {serifData.map((c, i) =>
                        [[c, tick, 1], [tick, c, -1]].map(([x, y, cnt], j) => (
                            <line key={`serif-${i}-${j}`}
                                  x1={x} y1={y}
                                  x2={cnt * x} y2={-cnt * y}
                                  className={classNames(styles.serif, ui.class.serif)}
                                  style={ui.style.serif}/>
                        ))
                    )}
                </g>

                <g className="labels">
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
                </g>

                <g className="figure">
                    <path d={`M 0 0 0 ${-2 * seg} A ${2 * seg} ${2 * seg} 0 0 1 ${2 * seg} 0 L ${2 * seg} ${2 * seg} 0 ${2 * seg} ${-2 * seg} 0 Z`}
                          className={classNames(styles.figure, ui.class.figure)}
                          style={ui.style.figure}/>
                </g>

                <g className="limits">
                    {limitCoords.map(({x, y, w, h}, i) => (
                        <rect key={`limit-${i}`}
                              x={x} y={y}
                              width={w}
                              height={h}
                              className={classNames(styles.limit, ui.class.limit)}
                              style={ui.style.limit}/>
                    ))}
                </g>

                <g className="points">
                    {points.map(({x, y, r, hit}, i) => (
                        <circle key={`point-${i}`}
                                cx={scaleToSVGCoords(x)} cy={-scaleToSVGCoords(y)} r="0.125rem"
                                className={classNames(styles.point, (R === String(r)) ? (hit ? styles.in : styles.out) : styles.inactive, ui.class.point)}
                                style={ui.style.point}/>
                    ))}
                </g>

            </svg>
        </div>
    );
}

// -------------------------------------------------------------------------------------------------------- //

Plane.propTypes = {
    length: PropTypes.number.isRequired,
    handler: PropTypes.func.isRequired,
    XMin: PropTypes.number,
    XMax: PropTypes.number,
    YMin: PropTypes.number,
    YMax: PropTypes.number,
    ui: PropTypes.shape({
        class: PropTypes.shape({
            plane: PropTypes.string,
            axis: PropTypes.string,
            arrow: PropTypes.string,
            serif: PropTypes.string,
            label: PropTypes.string,
            limit: PropTypes.string,
            limitHatch: PropTypes.string,
            figure: PropTypes.string,
            figureHatch: PropTypes.string,
            point: PropTypes.string,
        }),
        style: PropTypes.shape({
            plane: PropTypes.object,
            axis: PropTypes.object,
            arrow: PropTypes.object,
            serif: PropTypes.object,
            label: PropTypes.object,
            limit: PropTypes.object,
            limitHatch: PropTypes.object,
            figure: PropTypes.object,
            figureHatch: PropTypes.object,
            point: PropTypes.object,
        }),
    }),
}
