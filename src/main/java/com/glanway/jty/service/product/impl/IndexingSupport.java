package com.glanway.jty.service.product.impl;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vacoor.mux.common.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * TODO 最后更新时间,更新状态
 */
public abstract class IndexingSupport {
    private static final Logger LOG = LoggerFactory.getLogger(IndexingSupport.class);
    private SolrServer solrServer;

    /**
     * 更新 给定 id 的索引
     *
     * @param id     需要更新的 Document id
     * @param delete 是否是删除
     */
    protected void doUpdate(String id, boolean delete) {
        if (!StringUtils.hasText(id)) {
            return;
        }
        final SolrServer solrServer = getRequiredSolrServer();
        try {
            LOG.info("start update index...");

            Object bean = null;
            if (delete || null == (bean = getIndexedBean(id))) {
                LOG.info("delete document id: {}", id);
                solrServer.deleteById(id);
            } else {
                SolrInputDocument doc = solrServer.getBinder().toSolrInputDocument(bean);
                LOG.info("update document: {}", doc);
                solrServer.add(doc);
            }

            solrServer.commit();
        } catch (SolrServerException e) {
            LOG.error("update index failed.. {}", e);
            quietRollback(solrServer);
        } catch (IOException e) {
            LOG.error("update index failed.. {}", e);
            quietRollback(solrServer);
        }
    }

    /**
     * 重建索引
     */
    protected void doReindex() {
        final SolrServer solrServer = getRequiredSolrServer();
        LOG.info("start rebuild index...");
        try {
            solrServer.deleteByQuery("*:*");

            long count = countIndexedBeans();
            if (1 > count) {
                solrServer.commit();
                return;
            }

            int size = 1000;
            for (int offset = 0; offset < count; offset += size) {
                LOG.info("update {} to {}", offset, offset + size);
                List<?> beans = getPagedIndexedBeans(offset, size);
                if (null != beans) {
                    LOG.info("wait update size: " + beans.size());
                    solrServer.addBeans(beans);
                }
            }
            solrServer.commit();
            LOG.info("rebuild index complete, total: {}", count);
        } catch (IOException e) {
            LOG.error("rebuild index failed", e);
            quietRollback(solrServer);
        } catch (SolrServerException e) {
            LOG.error("rebuild index failed: {}", e);
            quietRollback(solrServer);
        }
    }

    protected void doTruncate() {
        final SolrServer solrServer = getRequiredSolrServer();
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (IOException e) {
            LOG.error("truncate index failed", e);
            quietRollback(this.solrServer);
        } catch (SolrServerException e) {
            LOG.error("truncate index failed: {}", e);
            quietRollback(this.solrServer);
        }
    }

    private void quietRollback(SolrServer solrServer) {
        try {
            solrServer.rollback();
        } catch (SolrServerException ignore) {
        } catch (IOException ignore) {
        }
    }

    private SolrServer getRequiredSolrServer() {
        SolrServer solrServer = getSolrServer();
        if (null == solrServer) {
            throw new IllegalStateException("No SolrServer found: no SolrServer configure?");
        }
        return solrServer;
    }

    protected abstract Object getIndexedBean(String id);

    protected abstract long countIndexedBeans();

    protected abstract List<?> getPagedIndexedBeans(int offset, int size);

    public SolrServer getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = solrServer;
    }
}
