import {JsonObject, JsonProperty} from 'json2typescript';


@JsonObject
export class ApiResponse {

    @JsonProperty('code', Number, true)
    code?: number = undefined;

    @JsonProperty('type', String, true)
    type?: string = undefined;

    @JsonProperty('message', String, true)
    message?: string = undefined;

}
