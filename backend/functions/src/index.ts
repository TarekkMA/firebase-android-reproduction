import * as admin from 'firebase-admin';
import * as functions from 'firebase-functions/v1';
import {beforeUserCreated} from 'firebase-functions/v2/identity';

admin.initializeApp();

export const authUserCreated = functions.auth.user().onCreate(async (user) => {
  console.log('authUserCreated', JSON.stringify(user));
});

export const authBeforeUserCreated = beforeUserCreated((event) => {
  console.log('authBeforeUserCreated', JSON.stringify(event));
});
