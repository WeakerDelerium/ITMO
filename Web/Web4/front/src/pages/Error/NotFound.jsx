import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Content} from "../../components/Layout/Content.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Error.module.scss";

// -------------------------------------------------------------------------------------------------------- //

export function NotFound() {
    return (
        <Content>
            <RedirectCaption desc="Страница не найдена"
                             link="На главную"
                             url="/"
                             ui={{...uiDefault, class: {wrap: styles.wrap}}}/>
        </Content>
    );
}
