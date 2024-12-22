import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import {ProtectedRoute} from "./components/ProtectedRoute.jsx";

// -------------------------------------------------------------------------------------------------------- //

import {Start} from "./pages/Start/Start.jsx";
import {Login} from "./pages/Auth/Login.jsx";
import {Register} from "./pages/Auth/Register.jsx";
import {Main} from "./pages/Main/Main.jsx";
import {NotAvailable} from "./pages/Error/NotAvailable.jsx";
import {NotFound} from "./pages/Error/NotFound.jsx";

// -------------------------------------------------------------------------------------------------------- //

import "./styles/reset.css";
import "./styles/index.scss";
import "./styles/scroll.scss";
import {useSelector} from "react-redux";

// -------------------------------------------------------------------------------------------------------- //

export function App() {

    const isAuth = useSelector(st => st.auth);

    return (
        <Router>
            <Routes>

                <Route path="/"
                       element={<Start/>}/>

                <Route path="/login"
                       element={
                           <ProtectedRoute allow={!isAuth} redirect="/main">
                               <Login/>
                           </ProtectedRoute>
                       }/>

                <Route path="/register"
                       element={
                           <ProtectedRoute allow={!isAuth} redirect="/main">
                               <Register/>
                           </ProtectedRoute>
                       }/>

                <Route path="/main"
                       element={
                           <ProtectedRoute allow={isAuth} redirect="/">
                               <Main/>
                           </ProtectedRoute>
                       }/>

                <Route path="/error"
                       element={<NotAvailable/>}/>

                <Route path="*"
                       element={<NotFound/>}/>

            </Routes>
        </Router>
    );

}
