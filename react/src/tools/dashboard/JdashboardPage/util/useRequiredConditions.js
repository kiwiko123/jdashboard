import { useEffect, useState } from 'react';
import { get } from 'lodash';
import logger from 'tools/monitoring/logging';

const STATUSES = {
    success: 'success',
    failed: 'failed',
    resolving: 'resolving',
};

export default function useRequiredConditions(conditions) {
    const hasNoConditions = !conditions || conditions.length === 0;
    const [status, setStatus] = useState(hasNoConditions ? STATUSES.success : STATUSES.resolving);
    useEffect(() => {
        if (status === STATUSES.success) {
            return;
        }

        const evaluatedPromises = conditions.map(condition => condition.condition());
        Promise.all(evaluatedPromises)
            .then((values) => {
                const failedConditionIndex = values.findIndex(v => !v);
                const evaluatedStatus = failedConditionIndex >= 0 ? STATUSES.failed : STATUSES.success;
                if (evaluatedStatus === STATUSES.failed) {
                    const failedConditionName = get(conditions, [failedConditionIndex, 'name'], '(unknown)');
                    logger.debug(`Failed condition ${failedConditionName}`);
                }
                setStatus(evaluatedStatus);
            });
    }, [conditions]);

    return status;
}