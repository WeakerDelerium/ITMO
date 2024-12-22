import {createSlice} from '@reduxjs/toolkit';
import {logout} from "../actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

const init = [];

// -------------------------------------------------------------------------------------------------------- //

const pointsSlice = createSlice({
    name: 'points',
    initialState: init,
    reducers: {
        setPoints: (state, action) => action.payload,
        addPoint: (state, action) => {
            state.push(action.payload);
        },
        // addPoint: (state, action) => [...state, action.payload],
    },
    extraReducers: builder => builder.addCase(logout, () => init),
});

// -------------------------------------------------------------------------------------------------------- //

export const {setPoints, addPoint} = pointsSlice.actions;
export const pointsReducer = pointsSlice.reducer;
