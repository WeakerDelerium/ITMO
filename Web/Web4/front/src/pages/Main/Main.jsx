import {Header} from "../../components/Layout/Header.jsx";
import {Footer} from "../../components/Layout/Footer.jsx";
import {Content} from "../../components/Layout/Content.jsx";
import {PointDashboard} from "../../components/PointDashboard/PointDashboard.jsx";

export function Main() {
    return(
        <>
            <Header/>
            <Content>
                <PointDashboard/>
            </Content>
            <Footer/>
        </>
    );
}