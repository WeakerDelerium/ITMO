import {Header} from "../../components/Layout/Header.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {Heading} from "../../components/Caption/Heading.jsx";
import {LinkButton} from "../../components/Button/Frame/LinkButton.jsx";

import styles from "./Start.module.scss";

export function Start() {
    return (
        <>
            <Header/>
            <Content>
                <div className={styles.wrapper}>
                    <Heading value="Добро пожаловать" className={styles.greeting}/>
                    <LinkButton url="/login" value="Sign In" className={styles.btn}/>
                    <LinkButton url="/register" value="Sign Up" className={styles.btn}/>
                </div>
            </Content>
            <Footer/>
        </>
    );
}