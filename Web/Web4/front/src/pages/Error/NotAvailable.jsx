import {uiDefault} from "../../utils/props.js";

// -------------------------------------------------------------------------------------------------------- //

import {Content} from "../../components/Layout/Content.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Error.module.scss";

// -------------------------------------------------------------------------------------------------------- //

export function NotAvailable() {
    return (
        <Content>
            <RedirectCaption desc="Извините, что-то пошло не так, пожалуйста, попробуйте позже"
                             link="На главную"
                             url="/"
                             ui={{...uiDefault, class: {wrap: styles.wrap}}}/>
        </Content>
    );
}
