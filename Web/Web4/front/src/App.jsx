import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

// -------------------------------------------------------------------------------------------------------- //

import {Start} from "./pages/Start/Start.jsx";
import {Login} from "./pages/Auth/Login.jsx";
import {Register} from "./pages/Auth/Register.jsx";
import {Main} from "./pages/Main/Main.jsx";
import {Error} from "./pages/Error.jsx";
import {NotFound} from "./pages/NotFound.jsx";

// -------------------------------------------------------------------------------------------------------- //

import "./styles/reset.css";
import "./styles/index.scss";

// -------------------------------------------------------------------------------------------------------- //

export function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Start/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/main" element={<Main/>}/>
                <Route path="/error" element={<Error/>}/>
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </Router>
    );
}
