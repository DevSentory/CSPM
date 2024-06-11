import Loading from '@/components/loading'
import { useEffect, useState } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
interface Props {
  showModal: boolean
  setShowModal: (newValue: boolean) => void
  resourceId: string
  scanTime: string
  accountId: string
  accountName: string
}

export default function ResourceScanModal({
  showModal,
  setShowModal,
  resourceId,
  scanTime,
  accountId,
  accountName,
}: Props) {
  const handleToggleModal = () => {
    setShowModal(false)
  }

  const [dataStatus, setDataStatus] = useState<any>(null)

  const fetchData = async () => {
    try {
      const response = await fetch(
        `${BASE_URL}/resources/${resourceId}/${scanTime}/${accountId}/${accountName}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json; charset=utf-8',
          },
          credentials: 'include',
        },
      )

      if (response.ok) {
        const inner = await response.json()
        const data = inner.result
        if (data.code === 2) {
          setDataStatus([])
        }
        setDataStatus(JSON.parse(data))
      } else {
        setDataStatus(null)
      }
    } catch (err) {
      console.error('Failed to fetch data', err)
      setDataStatus(null)
    }
  }

  useEffect(() => {
    if (showModal && resourceId) {
      fetchData()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [showModal, resourceId])

  return (
    <>
      {showModal && (
        <div className='bg-dimGray fixed inset-0 z-50  flex items-center justify-center'>
          <div className='relative h-[90%] w-[850px] rounded-lg bg-gray-50 shadow-sm'>
            <div className='flex justify-between p-3 text-start'>
              <h5 className='text-lg font-bold text-black'>리소스 상세 조회</h5>
              <div
                className='flex h-9 w-24 items-center justify-center rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleToggleModal}
              >
                취소
              </div>
            </div>

            <hr />

            <div className='left-0 h-[90%] w-full items-center justify-center overflow-y-auto text-black'>
              {dataStatus ? (
                <pre className='whitespace-pre-wrap'>
                  <code className='text-pretty leading-relaxed'>
                    {JSON.stringify(dataStatus, null, 2)}
                  </code>
                </pre>
              ) : (
                <div className='flex h-full w-full items-center justify-center'>
                  <Loading />
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  )
}
