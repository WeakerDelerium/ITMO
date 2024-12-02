import {Header} from "../../components/Layout/Header.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {LoginForm} from "../../components/Form/Auth/LoginForm.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

import styles from "./Auth.module.scss";

export function Login() {
    return (
        <>
            <Header/>
            <Content>
                <div className={styles.wrapper}>
                    <LoginForm to="/main"/>
                    <RedirectCaption desc="Нет аккаунта?" link="Создать" url="/register"/>
                </div>
            </Content>
            <Footer/>
        </>
    );
}