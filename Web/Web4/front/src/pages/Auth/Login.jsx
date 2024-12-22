import {Header} from "../../components/Layout/Header.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {LinkIconButton} from "../../components/Button/Icon/LinkIconButton.jsx";
import {LoginForm} from "../../components/Form/Auth/LoginForm.jsx";
import {RedirectCaption} from "../../components/Caption/RedirectCaption.jsx";

// -------------------------------------------------------------------------------------------------------- //

import ret from "../../assets/ret-arrow.svg";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Auth.module.scss";

// -------------------------------------------------------------------------------------------------------- //

export function Login() {
    return (
        <>
            <Header/>

            <Content>
                <div className={styles.wrapper}>

                    <LinkIconButton path={String(ret)}
                                    to={"/"}
                                    className={styles.return}/>

                    <LoginForm to="/main"/>

                    <RedirectCaption desc="Нет аккаунта?"
                                     link="Создать"
                                     url="/register"/>

                </div>
            </Content>

            <Footer/>
        </>
    );
}
