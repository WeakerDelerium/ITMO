import {configureStore} from '@reduxjs/toolkit';

// -------------------------------------------------------------------------------------------------------- //

import {RReducer} from './slices/rSlice.js';
import {stateReducer} from "./slices/stateSlice.js";
import {themeReducer} from "./slices/themeSlice.js";
import {planeLengthReducer} from "./slices/planeLengthSlice.js";
import {pointsReducer} from "./slices/pointsSlice.js";
import {authReducer} from "./slices/authSlice.js";

// -------------------------------------------------------------------------------------------------------- //

export const store = configureStore({
    reducer: {
        R: RReducer,
        state: stateReducer,
        theme: themeReducer,
        planeLength: planeLengthReducer,
        points: pointsReducer,
        auth: authReducer,
    },
});
