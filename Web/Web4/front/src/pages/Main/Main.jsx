import {useDispatch} from "react-redux";
import {logout} from "../../redux/actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

import {Header} from "../../components/Layout/Header.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {PointDashboard} from "../../components/PointDashboard/PointDashboard.jsx";
import {LogoutButton} from "../../components/Button/Icon/LogoutButton.jsx";

// -------------------------------------------------------------------------------------------------------- //

import ret from "../../assets/ret-arrow.svg";

// -------------------------------------------------------------------------------------------------------- //

import styles from "./Main.module.scss";

// -------------------------------------------------------------------------------------------------------- //

export function Main() {
    return (
        <>
            <Header/>

            <Content>
                <div className={styles.wrapper}>

                    <LogoutButton path={String(ret)}
                                  to={"/"}
                                  className={styles.return}/>

                    <PointDashboard/>

                </div>
            </Content>

            <Footer/>
        </>
    );
}
