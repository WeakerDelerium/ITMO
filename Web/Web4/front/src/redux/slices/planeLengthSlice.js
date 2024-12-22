import {createSlice} from '@reduxjs/toolkit';

// -------------------------------------------------------------------------------------------------------- //

const planeLengthSlice = createSlice({
    name: 'planeLength',
    initialState: 600,
    reducers: {
        setPlaneLength: (state, action) => action.payload,
    },
});

// -------------------------------------------------------------------------------------------------------- //

export const {setPlaneLength} = planeLengthSlice.actions;
export const planeLengthReducer = planeLengthSlice.reducer;
