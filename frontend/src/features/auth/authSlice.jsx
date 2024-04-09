import { getCurrentUser } from "@/api/authApi";
import { createSlice } from "@reduxjs/toolkit";

const currentUser = {
  id: 0,
  username: "",
  firstName: "",
  lastName: "",
  email: "",
  image: "",
  profileDescription: "",
};

export const authSlice = createSlice({
  name: "auth",
  initialState: currentUser,
  reducers: {
    loginSuccess: (state, action) => {
      state.id = action.payload.user.id;
      state.username = action.payload.user.username;
      state.email = action.payload.user.email;
      state.image = action.payload.user.image;
      state.profileDescription = action.payload.user.profileDescription;
      state.firstName = action.payload.user.firstName;
      state.lastName = action.payload.user.lastName;
    },
    logoutSuccess: (state, action) => {
      state.id = 0;
      delete state.username;
      delete state.email;
      delete state.image;
      delete state.profileDescription;
      delete state.firstName;
      delete state.lastName;
    },
    userUpdateSuccess: (state, action) => {
      state.username = action.payload.username;
      state.firstName = action.payload.firstName;
      state.lastName = action.payload.lastName;
      state.profileDescription = action.payload.profileDescription;
      state.image = action.payload.image;
    },
  },
});

export const { loginSuccess, logoutSuccess, userUpdateSuccess } =
  authSlice.actions;
export default authSlice.reducer;

// TODO: Cookie ile State yönetimini araştır.
export const fetchCurrentUser = () => async (dispatch) => {
  try {
    const response = await getCurrentUser();
    dispatch(loginSuccess(response.data));
  } catch (err) {
    if (err.response && err.response.status === 401) {
      dispatch(logoutSuccess());
    }
  }
};
