import {createRoot} from "react-dom/client"

import {Provider} from "react-redux";
import {store} from "./redux/store.js";

import {App} from "./App.jsx";

import "./styles/reset.css";
import {ThemeProvider} from "./components/ThemeProvider.jsx";

createRoot(document.getElementById("root")).render(
    <Provider store={store}>
        <ThemeProvider>
            <App/>
        </ThemeProvider>
    </Provider>
);
