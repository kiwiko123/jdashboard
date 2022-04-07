import { useEffect } from 'react';
import { get } from 'lodash';
import { goTo } from 'common/js/urltools';
import logger from 'tools/monitoring/logging';

export default function useRequiredConditions(conditions) {
    useEffect(() => {
        if (!conditions || conditions.length === 0) {
            return;
        }

        Promise.all(conditions.map(condition => condition.condition))
            .then((values) => {
                const failedConditionIndex = values.findIndex(v => !v);
                if (failedConditionIndex >= 0) {
                    const failedConditionName = get(conditions, [failedConditionIndex, 'name'], '(unknown)');
                    logger.error(`Failed condition ${failedConditionName}`);
                    goTo('/not-found');
                }
            });
    }, [conditions]);
}