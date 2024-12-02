import {Header} from "../../components/Layout/Header.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {RegisterForm} from "../../components/Form/Auth/RegisterForm.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

import styles from "./Auth.module.scss";

export function Register() {
    return (
        <>
            <Header/>
            <Content>
                <div className={styles.wrapper}>
                    <RegisterForm to="/main"/>
                    <RedirectCaption desc="Есть аккаунт?" link="Войти" url="/login"/>
                </div>
            </Content>
            <Footer/>
        </>
    );
}