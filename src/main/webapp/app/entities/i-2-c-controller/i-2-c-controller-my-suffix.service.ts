import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { I2cControllerMySuffix } from './i-2-c-controller-my-suffix.model';
@Injectable()
export class I2cControllerMySuffixService {

    private resourceUrl = 'api/i-2-c-controllers';

    constructor(private http: Http) { }

    create(i2cController: I2cControllerMySuffix): Observable<I2cControllerMySuffix> {
        let copy: I2cControllerMySuffix = Object.assign({}, i2cController);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(i2cController: I2cControllerMySuffix): Observable<I2cControllerMySuffix> {
        let copy: I2cControllerMySuffix = Object.assign({}, i2cController);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<I2cControllerMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }



    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
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
