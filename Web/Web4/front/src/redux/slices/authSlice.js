import {createSlice} from '@reduxjs/toolkit';
import {logout} from "../actions/authAction.js";

// -------------------------------------------------------------------------------------------------------- //

import {authenticated} from "../../utils/axios.js";

// -------------------------------------------------------------------------------------------------------- //

const authSlice = createSlice({
    name: 'auth',
    initialState: authenticated(),
    reducers: {
        setAuth: (state, action) => action.payload,
    },
    extraReducers: builder => builder.addCase(logout, () => false),
});

// -------------------------------------------------------------------------------------------------------- //

export const {setAuth} = authSlice.actions;
export const authReducer = authSlice.reducer;
