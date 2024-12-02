import { configureStore } from '@reduxjs/toolkit';

import {RReducer} from './slices/RSlice.js';
import {stateReducer} from "./slices/StateSlice.js";
import {themeReducer} from "./slices/ThemeSlice.js";

export const store = configureStore({
    reducer: {
        R: RReducer,
        state: stateReducer,
        theme: themeReducer,
    },
});
