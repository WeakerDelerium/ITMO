import {createSlice} from "@reduxjs/toolkit";
import {logout} from "../actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

const init = null;

// -------------------------------------------------------------------------------------------------------- //

const stateSlice = createSlice({
    name: 'state',
    initialState: init,
    reducers: {
        setState: (state, action) => action.payload,
        resetState: () => init,
    },
    extraReducers: builder => builder.addCase(logout, () => init),
});

// -------------------------------------------------------------------------------------------------------- //

export const {setState, resetState} = stateSlice.actions;
export const stateReducer = stateSlice.reducer;
