import {createSlice} from "@reduxjs/toolkit";

const rSlice = createSlice({
    name: 'R',
    initialState: null,
    reducers: {
        setR: (state, action) => action.payload,
    },
});

export const {setR} = rSlice.actions;

export const RReducer = rSlice.reducer;
