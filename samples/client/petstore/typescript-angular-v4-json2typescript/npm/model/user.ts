import {JsonObject, JsonProperty} from 'json2typescript';


@JsonObject
export class User {

    @JsonProperty('id', Number, true)
    id?: number = undefined;

    @JsonProperty('username', String, true)
    username?: string = undefined;

    @JsonProperty('firstName', String, true)
    firstName?: string = undefined;

    @JsonProperty('lastName', String, true)
    lastName?: string = undefined;

    @JsonProperty('email', String, true)
    email?: string = undefined;

    @JsonProperty('password', String, true)
    password?: string = undefined;

    @JsonProperty('phone', String, true)
    phone?: string = undefined;

        /**
        * User Status
        */
    @JsonProperty('userStatus', Number, true)
    userStatus?: number = undefined;

}
