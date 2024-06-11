import { useScanResultException } from '@/components/Zustand/scanExceptionStore'
import { useTrigger } from '@/components/Zustand/store'
import { useState, useEffect } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL
const DOMAIN_URL = process.env.NEXT_PUBLIC_NEXT_APP_DOMAIN_URL
interface Props {
  showModal: boolean
  setShowModal: (newValue: boolean) => void
  index: number
}

function ResultKeyInModal({ showModal, setShowModal }: Props) {
  const [textBoxValue, setTextBoxValue] = useState('')
  const [selectedOption, setSelectedOption] = useState('')
  const { accountIdException, resourceIdException, policyTitleException, accountNameException } =
    useScanResultException()
  const [postResult, setPostResult] = useState('')

  const exceptionPostList = {
    resourceId: resourceIdException,
    accountId: accountIdException,
    accountName: accountNameException,
    policyTitle: policyTitleException,
    exceptionContent: textBoxValue,
    selectedOption: selectedOption,
  }

  const exceptionPost = async () => {
    try {
      const response = await fetch(`${BASE_URL}/compliances/exception`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          Origin: `${DOMAIN_URL}`,
        },
        credentials: 'include',
        body: JSON.stringify(exceptionPostList),
      })

      if (response.ok) {
        const inner = await response.json()
        if (inner.code === 0) {
          setPostResult(inner.message)
        }
        if (inner.code === 12) {
          setPostResult('Fail: compliance 조회할 데이터가 없습니다.')
        }
      }
    } catch (error) {
      setPostResult('Fail: ' + error)
    }
  }

  const handleOptionChange = (event: any) => {
    setSelectedOption(event.target.value)
  }

  const handleConfirm = () => {
    exceptionPost() // 모달 닫기
  }

  const { setFetchTrigger } = useTrigger()
  const handleCancel = () => {
    setTextBoxValue('')
    setFetchTrigger()
    setShowModal(false) // 모달 닫기
  }

  useEffect(() => {
    if (showModal) {
      // 모달이 열릴 때 body에 스크롤 막기
      document.body.style.overflow = 'hidden'
    } else {
      // 모달이 닫힐 때 body 스크롤 허용
      document.body.style.overflow = 'unset'
    }
  }, [showModal])

  useEffect(() => {
    setPostResult('')
  }, [showModal])

  if (!showModal) return null // showModal이 false면 모달을 렌더링하지 않음
  return (
    <div className='bg-dimGray fixed inset-0 z-50 flex items-center justify-center'>
      <div className='itmes-center h-[250px] w-[400px] justify-center rounded-lg bg-gray-50 shadow-md'>
        <h2 className='flex h-[30px] items-center justify-center'>취약점 결과 Update</h2>
        <hr />
        {postResult === '' ? (
          <div>
            <div className='mt-3 flex justify-center gap-10'>
              <label>
                <input
                  type='radio'
                  name='options'
                  value='Exception'
                  checked={selectedOption === 'Exception'}
                  onChange={handleOptionChange}
                />
                <span className='ml-1'>Exception</span>
              </label>
              <label>
                <input
                  type='radio'
                  name='options'
                  value='Open'
                  checked={selectedOption === 'Open'}
                  onChange={handleOptionChange}
                />
                <span className='ml-1'>Open</span>
              </label>
              <label>
                <input
                  type='radio'
                  name='options'
                  value='Close'
                  checked={selectedOption === 'Close'}
                  onChange={handleOptionChange}
                />
                <span className='ml-1'>Close</span>
              </label>
            </div>
            <div className='itmes-center mt-2 flex w-full justify-center '>
              {/* 텍스트 창 */}
              <textarea
                className='w-[95%] border p-1'
                value={textBoxValue}
                onChange={(e) => setTextBoxValue(e.target.value)}
                placeholder='내용을 입력해주세요.'
                rows={5} // 텍스트 창 행 수 설정
              />
            </div>
            <div className='mt-1 text-right'>
              <button
                className='mr-2 rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleConfirm}
              >
                확인
              </button>
              <button
                className='mr-2 rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleCancel}
              >
                취소
              </button>
            </div>
          </div>
        ) : (
          <div className='h-[80%] w-full flex-col items-center justify-center'>
            <div className='flex h-[80%] w-[full] items-center justify-center'>{postResult}</div>

            <div className='flex h-[20%] w-full items-center justify-center'>
              <button
                className='mr-2 rounded-md bg-kyboNavy px-4 py-2 text-white hover:bg-gray-300'
                onClick={handleCancel}
              >
                확인
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default ResultKeyInModal
