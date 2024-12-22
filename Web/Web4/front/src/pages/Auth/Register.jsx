import {Header} from "../../components/Layout/Header.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {LinkIconButton} from "../../components/Button/Icon/LinkIconButton.jsx";
import {RegisterForm} from "../../components/Form/Auth/RegisterForm.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

// -------------------------------------------------------------------------------------------------------- //

import ret from "../../assets/ret-arrow.svg";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Auth.module.scss";

// -------------------------------------------------------------------------------------------------------- //

export function Register() {
    return (
        <>
            <Header/>

            <Content>
                <div className={styles.wrapper}>

                    <LinkIconButton path={String(ret)}
                                    to={"/"}
                                    className={styles.return}/>

                    <RegisterForm to="/main"/>

                    <RedirectCaption desc="Есть аккаунт?"
                                     link="Войти"
                                     url="/login"/>

                </div>
            </Content>

            <Footer/>
        </>
    );
}
