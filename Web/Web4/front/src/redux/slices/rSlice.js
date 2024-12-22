import {createSlice} from "@reduxjs/toolkit";
import {logout} from "../actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

const init = null;

// -------------------------------------------------------------------------------------------------------- //

const rSlice = createSlice({
    name: 'R',
    initialState: init,
    reducers: {
        setR: (state, action) => action.payload,
    },
    extraReducers: builder => builder.addCase(logout, () => init),
});

// -------------------------------------------------------------------------------------------------------- //

export const {setR} = rSlice.actions;
export const RReducer = rSlice.reducer;
