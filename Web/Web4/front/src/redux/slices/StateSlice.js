import {createSlice} from "@reduxjs/toolkit";

const stateSlice = createSlice({
    name: 'state',
    initialState: null,
    reducers: {
        setState: (state, action) => action.payload,
    },
});

export const {setState} = stateSlice.actions;

export const stateReducer = stateSlice.reducer;
