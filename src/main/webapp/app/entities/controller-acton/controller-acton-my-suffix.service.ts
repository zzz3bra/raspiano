import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ControllerActonMySuffix } from './controller-acton-my-suffix.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ControllerActonMySuffixService {

    private resourceUrl = 'api/controller-actons';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(controllerActon: ControllerActonMySuffix): Observable<ControllerActonMySuffix> {
        const copy: ControllerActonMySuffix = Object.assign({}, controllerActon);
        copy.actionStart = this.dateUtils.toDate(controllerActon.actionStart);
        copy.actionEnd = this.dateUtils.toDate(controllerActon.actionEnd);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(controllerActon: ControllerActonMySuffix): Observable<ControllerActonMySuffix> {
        const copy: ControllerActonMySuffix = Object.assign({}, controllerActon);

        copy.actionStart = this.dateUtils.toDate(controllerActon.actionStart);

        copy.actionEnd = this.dateUtils.toDate(controllerActon.actionEnd);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ControllerActonMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.actionStart = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.actionStart);
            jsonResponse.actionEnd = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.actionEnd);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].actionStart = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].actionStart);
            jsonResponse[i].actionEnd = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].actionEnd);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
